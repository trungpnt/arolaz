
package com.ecommerce.arolaz.Model;

import com.ecommerce.arolaz.Product.Model.Product;
import org.bson.types.ObjectId;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class ProductTest {
    private static final String productId = "acb123";
    private static final ObjectId PRODUCT_ID = new ObjectId(productId);
    private static final String CATEGORY_ID = "4";
    private static final String NAME = "Adidas Hoodie";
    private static final String IMG_URL = "Url Picture";
    private static final Double BASIC_SMALL_SIZE_PRICE = 220000.0;
    private static final String DESCRIPTION = "..of best quality";

    @Test
    public void testConstructorAndGetters() throws Exception {
        Product product = new Product(PRODUCT_ID,NAME,BASIC_SMALL_SIZE_PRICE,IMG_URL,DESCRIPTION);

        assertThat(product.getProductId(), is(PRODUCT_ID));
        assertThat(product.getProductName(), is(NAME));
        assertThat(product.getImgUrl(), is(IMG_URL));
        assertThat(product.getBasicSmallSizePrice(), is(BASIC_SMALL_SIZE_PRICE));
        assertThat(product.getDescription(), is(DESCRIPTION));
    }

    @Test
    public void equalsHashcodeVerify() {
        Product product1 = new Product(PRODUCT_ID,CATEGORY_ID,NAME,BASIC_SMALL_SIZE_PRICE,IMG_URL,DESCRIPTION);
        Product product2 = new Product(PRODUCT_ID,CATEGORY_ID,NAME,BASIC_SMALL_SIZE_PRICE,IMG_URL,DESCRIPTION);
        assertThat(product1, is(product2));
        assertThat(product1.hashCode(), is(product2.hashCode()));
    }
}

