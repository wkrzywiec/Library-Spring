package com.wkrzywiec.spring.library.retrofit;

import com.wkrzywiec.spring.library.retrofit.model.BookDetailsRespond;
import com.wkrzywiec.spring.library.retrofit.model.GoogleBookRespond;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GoogleBookAPI {

	@GET("/books/v1/volumes?langRestrict=en&maxResults=40&printType=books")
	public Call<GoogleBookRespond> searchBooks(@Query("q") String searchText,
												@Query("key") String key);
	
	@GET("/books/v1/volumes/{googleId}")
	public Call<BookDetailsRespond> getBookDetails(@Path("googleId") String googleId,
													@Query("key") String key);
} 
