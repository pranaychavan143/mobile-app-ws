package com.tst.mobileappws.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tst.mobileappws.io.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);

	@Query(value = "select  * from Users u where u.Email_VERIFICATION_STATUS ='true' ",
			countQuery = "select count (*) from Users u where u.Email_VERIFICATION_STATUS ='true' ",
			nativeQuery = true)
	Page<UserEntity>findAllUserWithConfirmedEmailAddress(Pageable pagebleRequest);

	@Query(value = "select * form Users u where u.first_name=?1",nativeQuery = true)
	List<UserEntity> findUserByFirstName(String firstName);

	@Query(value = "select * from Users u where  u.last_name=:lastName",nativeQuery = true)
	List<UserEntity>findUserByLastName(@Param("lastName") String lastName);

	@Query(value = "select * from Users u where first_name LIKE %:keyword% or last_name LIKE %:keyword%",nativeQuery = true)
	List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);

	@Query(value = "select u.first_name, u.last_name from Users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%",nativeQuery = true)
	List<Object[]>findUserFirstNameAndLastNameByKeyword(@Param("keyword")String keyword);

	@Transactional
	@Modifying
	@Query(value = "update Users u set u.EMAIL_VERIFICATION_STATUS=:emailVerificationStatus where u.user_Id=:userId", nativeQuery = true)
	void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);

	//Java Persistance Query  Language(JPQL)

	@Query("select user from UserEntity user where user.userId=:userId")
	UserEntity findUserEntityByUserId(@Param("userId") String userId);

	@Query("select user.firstName, user.lastName from UserEntity user where user.userId=:userId")
	List<Object[]>getUserEntityFullNameByUserId(@Param("userId")String userId);

	@Transactional
	@Modifying
	@Query("update UserEntity user set user.emailVerificationStatus=:emailVerificationStatus where user.userId=:userId")
	void updateUserEntityEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);
}
