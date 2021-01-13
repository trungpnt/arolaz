package com.ecommerce.arolaz.Utils.S3BucketFileHandler.Controller;

import com.ecommerce.arolaz.Utils.S3BucketFileHandler.ProductImgUrlResponseModel;
import com.ecommerce.arolaz.Utils.S3BucketFileHandler.Service.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value= "/api")
public class AWSS3Controller {

	@Autowired
	private AWSS3Service service;

	@PostMapping(value= "/upload")
	public ResponseEntity<ProductImgUrlResponseModel> uploadFile(/*@RequestPart(value= "file")*/ final MultipartFile multipartFile) {

		ProductImgUrlResponseModel productImgUrlResponseModel = service.uploadFile(multipartFile);
		final String imgUrl = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
		productImgUrlResponseModel.setFileName(imgUrl);
		return new ResponseEntity<>(productImgUrlResponseModel, HttpStatus.CREATED);
	}
}
