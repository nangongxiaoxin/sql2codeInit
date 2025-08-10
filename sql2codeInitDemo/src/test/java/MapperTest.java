import com.slilio.sql2codeInitDemo.RunDemoApplication;
import com.slilio.sql2codeInitDemo.entity.po.ProductInfo;
import com.slilio.sql2codeInitDemo.entity.query.ProductInfoQuery;
import com.slilio.sql2codeInitDemo.mappers.ProductInfoMapper;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
  @Resource private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

  @Test
  public void mapTest() throws ParseException {
    ProductInfoQuery query = new ProductInfoQuery();

    query.setId(5);
    query.setCreateTimeStart("2017-05-04");
    query.setCodeFuzzy("I");

    List<ProductInfo> dataList = productInfoMapper.selectList(query);
    System.out.println(dataList.size());
    for (ProductInfo productInfo : dataList) {
      System.out.println(productInfo);
    }

    Integer count = productInfoMapper.selectCount(query);
    System.out.println(count);
  }

  @Test
  public void insertTest() {
    ProductInfo productInfo = new ProductInfo();
    productInfo.setCode("2202");
    productInfo.setSkuType(6);
    productInfo.setColorType(0);
    productInfo.setCreateTime(new Date());
    productInfo.setCreateDate(new Date());

    productInfoMapper.insert(productInfo);

    System.out.println(productInfo.getId());
  }

  @Test
  public void inertOrUpdateTest() {
    ProductInfo productInfo = new ProductInfo();
    productInfo.setCode("fzlMfY43Cs");
    productInfo.setProductName("测试");

    productInfoMapper.insertOrUpdate(productInfo);

    System.out.println(productInfo.getId());
  }

  @Test
  public void insertBatchTest() {
    List<ProductInfo> productInfoList = new ArrayList<>();
    ProductInfo productInfo = new ProductInfo();
    productInfo.setCode("1111111");
    productInfo.setCreateTime(new Date());
    productInfoList.add(productInfo);

    productInfo = new ProductInfo();
    productInfo.setCode("12311456");
    productInfo.setCreateTime(new Date());
    productInfoList.add(productInfo);

    productInfoMapper.insertOrUpdateBatch(productInfoList);

    System.out.println(productInfo.getId());
  }

  @Test
  public void selectByKeyTest() {
    ProductInfo productInfo = productInfoMapper.selectById(5);
    System.out.println(productInfo);

    ProductInfo productInfo1 = productInfoMapper.selectByCode("1111");
    System.out.println(productInfo1);

    ProductInfo productInfo2 = productInfoMapper.selectBySkuTypeAndColorType(3, 14);
    System.out.println(productInfo2);
  }

  @Test
  public void updateByKeyTest() {
    ProductInfo productInfo = new ProductInfo();
    productInfo.setProductName("update by 6");
    productInfoMapper.updateById(productInfo, 6);

    productInfo = new ProductInfo();
    productInfo.setProductName("update by code");
    productInfoMapper.updateByCode(productInfo, "1111");

    productInfo = new ProductInfo();
    productInfo.setProductName("update by 1111111 and 12311456");
    productInfoMapper.updateBySkuTypeAndColorType(productInfo, 1, 2);
  }

  @Test
  public void deleteByKeyTest() {
    productInfoMapper.deleteById(33);
    productInfoMapper.deleteByCode("1111111");
    productInfoMapper.deleteBySkuTypeAndColorType(6, 0);
  }
}
