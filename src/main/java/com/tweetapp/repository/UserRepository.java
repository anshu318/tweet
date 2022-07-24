package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.tweetapp.domain.UserModel;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Repository("user-repository")
public interface UserRepository extends MongoRepository<UserModel, String> {

	UserModel findByUsername(String username);

	@Query("{'username':{'$regex':'?0','$options':'i'}}")
	List<UserModel> searchByUsername(String username);
}
