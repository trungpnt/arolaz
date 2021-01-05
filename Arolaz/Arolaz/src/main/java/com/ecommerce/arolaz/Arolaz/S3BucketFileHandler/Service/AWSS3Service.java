package com.ecommerce.arolaz.Arolaz.S3BucketFileHandler.Service;

import com.ecommerce.arolaz.Arolaz.S3BucketFileHandler.ProductImgUrlResponseModel;
import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

	ProductImgUrlResponseModel uploadFile(MultipartFile multipartFile);
}
