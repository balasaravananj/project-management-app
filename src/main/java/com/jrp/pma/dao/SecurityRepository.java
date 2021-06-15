package com.jrp.pma.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jrp.pma.entity.UserAccount;

public interface SecurityRepository extends PagingAndSortingRepository<UserAccount,Long> {

}
