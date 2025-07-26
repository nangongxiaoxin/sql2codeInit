import com.slilio.sql2codeInitDemo.RunDemoApplication;
import com.slilio.sql2codeInitDemo.entity.po.ProductInfo;
import com.slilio.sql2codeInitDemo.entity.query.ProductInfoQuery;
import com.slilio.sql2codeInitDemo.mappers.ProductInfoMappers;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: slilio @CreateTime: 2025-07-26 @Description: @Version: 1.0
 */
@SpringBootTest(classes = RunDemoApplication.class)
@RunWith(SpringRunner.class)
public class MapperTest {
  @Resource private ProductInfoMappers<ProductInfo, ProductInfoQuery> productInfoMapper;

  @Test
  public void mapTest() {
    List<ProductInfo> dataList = productInfoMapper.selectList(new ProductInfoQuery());
    System.out.println(dataList.size());
    for (ProductInfo productInfo : dataList) {
      System.out.println(productInfo);
    }
  }
}
