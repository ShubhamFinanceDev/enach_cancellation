package com.eNach_Cancellation.Repository;

import com.eNach_Cancellation.Entity.StatusManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusManage,Long> {

}