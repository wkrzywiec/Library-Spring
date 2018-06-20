package com.wkrzywiec.spring.library.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.wkrzywiec.spring.library.dto.BookDTO;
import com.wkrzywiec.spring.library.retrofit.GoogleBookAPI;
import com.wkrzywiec.spring.library.retrofit.model.BookDetailsRespond;
import com.wkrzywiec.spring.library.retrofit.model.GoogleBookRespond;
import com.wkrzywiec.spring.library.retrofit.model.ItemAPIModel;
import com.wkrzywiec.spring.library.retrofit.model.VolumeInfoModel;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
@PropertySource(value = {"classpath:properties/googleAPI.properties"})
public class GoogleBookServiceImpl implements GoogleBookService {
	
	@Value("${googleAPI.key}")
	private String googleAPIKey;

	@Override
	public List<BookDTO> searchBookList(String searchText) {
		
		List<ItemAPIModel> itemsAPIList = null;
		List<BookDTO> booksList = null;
		itemsAPIList = this.searchAPIItemsList(searchText);
		booksList = this.convertAPIResultToBookDTO(itemsAPIList);
		
		return booksList;
	}
	
	@Override
	public BookDTO getSingleBook(String googleId) {
		
		BookDTO bookDTO = null;
		
		VolumeInfoModel volume = null;
		BookDetailsRespond respond = null;
		
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		Retrofit retrofit = new Retrofit.Builder()
		  .baseUrl("https://www.googleapis.com/")
		  .addConverterFactory(GsonConverterFactory.create())
		  .client(httpClient.build())
		  .build();
		
		GoogleBookAPI googleBookApi = retrofit.create(GoogleBookAPI.class);
		Call<BookDetailsRespond> callSync = googleBookApi.getBookDetails(googleId, googleAPIKey);
		
		try {
			Response<BookDetailsRespond> response = callSync.execute();
			respond = response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		volume = respond.getVolumeInfo();
		bookDTO = this.convertVolumeInfoToBookDTO(volume, googleId);
		
		return bookDTO;
	}

	private List<ItemAPIModel> searchAPIItemsList(String searchText){
		
		List<ItemAPIModel> bookList = null;
		GoogleBookRespond respond = null;
		
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		Retrofit retrofit = new Retrofit.Builder()
		  .baseUrl("https://www.googleapis.com/")
		  .addConverterFactory(GsonConverterFactory.create())
		  .client(httpClient.build())
		  .build();
		
		GoogleBookAPI googleBookApi = retrofit.create(GoogleBookAPI.class);
		Call<GoogleBookRespond> callSync = googleBookApi.searchBooks(searchText, googleAPIKey);
		
		try {
			Response<GoogleBookRespond> response = callSync.execute();
			respond = response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bookList = respond.getItems();
		
		return bookList;
	}
	
	private BookDTO convertVolumeInfoToBookDTO(VolumeInfoModel volume, String googleId) {
		
		BookDTO book = new BookDTO();
		
		book.setGoogleId(googleId);
		book.setTitle(volume.getTitle());
		book.setAuthors(volume.getAuthors());
		book.setPublisher(volume.getPublisher());
		book.setPublishedDate(volume.getPublishedDate());
		book.setIsbn_10(volume.getIndustryIdentifiers().get(0).getIdentifier());
		book.setIsbn_13(volume.getIndustryIdentifiers().get(1).getIdentifier());
		book.setRating(volume.getAverageRating());
		book.setPageCount(volume.getPageCount());
		book.setBookCategories(volume.getCategories());
		book.setImageLink(volume.getImageLinks().getThumbnail());
		book.setDescription(volume.getDescription());
			
		return book;
	}
	
	private List<BookDTO> convertAPIResultToBookDTO(List<ItemAPIModel> itemsAPIList){
		
		List<BookDTO> booksList = new ArrayList<BookDTO>();
		BookDTO book = null;
		
		for (ItemAPIModel apiModel: itemsAPIList) {
			
			System.out.println(apiModel.getVolumeInfo());
			
			book = new BookDTO();
			book.setGoogleId(apiModel.getId());
			book.setTitle(apiModel.getVolumeInfo().getTitle());
			book.setAuthors(apiModel.getVolumeInfo().getAuthors());
			book.setPublisher(apiModel.getVolumeInfo().getPublisher());
			book.setPublishedDate(apiModel.getVolumeInfo().getPublishedDate());
			
			book.setRating(apiModel.getVolumeInfo().getAverageRating());
			book.setPageCount(apiModel.getVolumeInfo().getPageCount());
			if (apiModel.getVolumeInfo().getImageLinks() != null) 
				book.setImageLink(apiModel.getVolumeInfo().getImageLinks().getThumbnail());
			book.setDescription(apiModel.getVolumeInfo().getDescription());
			book.setBookCategories(apiModel.getVolumeInfo().getCategories());
			
			//book.setIsbn_10(apiModel.getVolumeInfo().getIndustryIdentifiers());
			//book.setIsbn_13(apiModel.getVolumeInfo().getIndustryIdentifiers());
			
			booksList.add(book);
		}
		
		return booksList;
	}

}
