package com.ecommerce.arolaz.Size.Service;

import com.ecommerce.arolaz.Size.Model.Size;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface SizeService {
    Optional<Size> findBySizeName(String sizeName);
    Optional<Size> findBySizeId(ObjectId sizeId);
}
