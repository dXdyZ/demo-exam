package com.example.demoexamen.file_import;

import com.example.demoexamen.componentn.CustomDateFormatter;
import com.example.demoexamen.entity.*;
import com.example.demoexamen.exception.PartnerAndProductNotFound;
import com.example.demoexamen.exception.ProductTypeNotFoundException;
import com.example.demoexamen.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class DataImportService {

    private final ProductService productService;
    private final PartnerService partnerService;
    private final ProductTypeService productTypeService;
    private final MaterialService materialService;
    private final SalesHistoryService salesHistoryService;

    public DataImportService(ProductService productService, PartnerService partnerService, ProductTypeService productTypeService, MaterialService materialService, SalesHistoryService salesHistoryService) {
        this.productService = productService;
        this.partnerService = partnerService;
        this.productTypeService = productTypeService;
        this.materialService = materialService;
        this.salesHistoryService = salesHistoryService;
    }


    public void readPartnerFromExel(String filePath) throws Exception {
        partnerService.savePartners(getRow(filePath).stream()
                .map(row -> {
            Partner partner = new Partner();
            partner.setPartnerType(row.getCell(0).getStringCellValue());
            partner.setName(row.getCell(1).getStringCellValue());
            partner.setDirector(row.getCell(2).getStringCellValue());
            partner.setEmail(row.getCell(3).getStringCellValue());
            partner.setPhoneNumber(row.getCell(4).getStringCellValue());
            partner.setAddress(row.getCell(5).getStringCellValue());
            partner.setInn((long) row.getCell(6).getNumericCellValue());
            partner.setRating((int) row.getCell(7).getNumericCellValue());
            return partner;
        }).toList());
    }

    public void readProductFromExel(String filePath) throws Exception {
        productService.saveProducts(getRow(filePath).stream()
                .map(row -> {
                    if (!productTypeService.getAllProductType().isEmpty()) {
                        Product product = new Product();
                        product.setProductType(productTypeService.getProductTypeByName(row.getCell(0).getStringCellValue()));
                        product.setName(row.getCell(1).getStringCellValue());
                        product.setArticle((long) row.getCell(2).getNumericCellValue());
                        product.setMinPrice(row.getCell(3).getNumericCellValue());
                        return product;
                    } else throw new ProductTypeNotFoundException("Product type not found");
                })
                .toList());
    }

    public void readProductTypeFormExel(String filePath) throws Exception {
        productTypeService.saveProductType(getRow(filePath).stream()
                .filter(row -> row != null &&
                        !getCellValueAsString(row.getCell(0)).isBlank() &&
                        !getCellValueAsString(row.getCell(1)).isBlank())
                .map(row -> {
                    return ProductType.builder()
                            .typeName(getCellValueAsString(row.getCell(0)))
                            .coefficient(new BigDecimal(getCellValueAsString(row.getCell(1))
                                    .replace(",", ".")))
                            .build();
                })
                .toList());
    }

    public void readMaterialFromExel(String filePath) throws Exception {
        materialService.saveMaterials(getRow(filePath).stream()
                .map(row -> {
                    return Material.builder()
                            .materialName(row.getCell(0).getStringCellValue())
                            .defectPercent(new BigDecimal(getCellValueAsString(row.getCell(1))
                                    .replace("%", "")
                                    .replace(",", ".")))
                            .build();
                }).toList());
    }

    public void readSalesHistoryFromExel(String filePath) throws Exception {
        salesHistoryService.saveSalesHistories(getRow(filePath).stream()
                .map(row -> {

                    log.info("Date: {}", getCellValueAsString(row.getCell(3)));

                    if (!partnerService.getAllPartner().isEmpty() && !productService.getAllProduct().isEmpty()) {
                        return SalesHistory.builder()
                                .product(productService.getProductByName(getCellValueAsString(row.getCell(0))))
                                .partner(partnerService.getPartnerByName(getCellValueAsString(row.getCell(1))))
                                .quantity(Integer.valueOf(getCellValueAsString(row.getCell(2))))
                                .salesDate(CustomDateFormatter.localDateFormatter(getCellValueAsString(row.getCell(3))))
                                .build();
                    } throw new PartnerAndProductNotFound("Not exists data in table product and partner");
                }).toList());
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private List<Row> getRow(String filePath) throws Exception {
        List<Row> rows = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(new File(filePath))) {
            Sheet sheet = workbook.getSheetAt(0); //берем первый лист
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) rowIterator.next();
            rowIterator.forEachRemaining(rows::add);
        }
        return rows;
    }
}







