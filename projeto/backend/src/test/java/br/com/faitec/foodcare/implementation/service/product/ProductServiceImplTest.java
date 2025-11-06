package br.com.faitec.foodcare.implementation.service.product;

import br.com.faitec.foodcare.domain.Product;
import br.com.faitec.foodcare.port.dao.product.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product validProduct;

    @BeforeEach
    void setUp() {
        validProduct = new Product(1, "Arroz", Product.ProductType.basic, 1, Product.MeasureType.kg, null);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(productDao.create(validProduct)).thenReturn(1);

        int result = productService.create(validProduct);

        assertEquals(1, result);
        verify(productDao).create(validProduct);
    }

    @Test
    void shouldReturnInvalidResponseWhenCreatingNullProduct() {
        int result = productService.create(null);

        assertEquals(-1, result);
        verify(productDao, never()).create(any());
    }

    @Test
    void shouldFindProductById() {
        when(productDao.findByid(1)).thenReturn(validProduct);

        Product result = productService.findById(1);

        assertNotNull(result);
        assertEquals("Arroz", result.getName());
        verify(productDao).findByid(1);
    }

    @Test
    void shouldUpdateProductNameSuccessfully() {
        when(productDao.findByid(1)).thenReturn(validProduct);
        when(productDao.updateNameDao(1, "Feijão")).thenReturn(true);

        boolean result = productService.updateName(1, "Feijão");

        assertTrue(result);
        verify(productDao).updateNameDao(1, "Feijão");
    }
}