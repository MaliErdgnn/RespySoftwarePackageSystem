package com.mehmetalierdogan.RepsySoftwarePackageSystem.DataAccessLayer;

import com.mehmetalierdogan.RepsySoftwarePackageSystem.Entity.PackageEntity;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.Entity.PackageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PackageRepository extends JpaRepository<PackageEntity, PackageId> {
}