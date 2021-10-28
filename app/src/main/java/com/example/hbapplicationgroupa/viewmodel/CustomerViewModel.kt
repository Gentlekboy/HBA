package com.example.hbapplicationgroupa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hbapplicationgroupa.adapter.pastbookings_adapter.PastBookingPagingDataSource
import com.example.hbapplicationgroupa.model.customermodule.getcustomerbookingbyuserid.BookingByUserIdResponseItems
import com.example.hbapplicationgroupa.network.CustomerModuleApiInterface
import com.example.hbapplicationgroupa.database.AuthPreference
import com.example.hbapplicationgroupa.model.customermodule.addcustomerratingsbyhotelid.HotelIdRatingsModel
import com.example.hbapplicationgroupa.model.customermodule.addcustomerratingsbyhotelid.RatingsByHotelIdResponseModel
import com.example.hbapplicationgroupa.model.customermodule.addcustomerreviewbyhotelid.HotelIdModel
import com.example.hbapplicationgroupa.model.customermodule.addcustomerreviewbyhotelid.ReviewByHotelIdResponseModel
import com.example.hbapplicationgroupa.repository.customermodulerepository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(private val customerRepository: CustomerRepository, private val api: CustomerModuleApiInterface): ViewModel() {
//class CustomerViewModel @Inject constructor(private val customerRepository: CustomerRepository): ViewModel() {
    private val _addReviewResponse: MutableLiveData<ReviewByHotelIdResponseModel> = MutableLiveData()
    val addReviewResponse: LiveData<ReviewByHotelIdResponseModel> = _addReviewResponse

    private val _addRatingsResponse: MutableLiveData<RatingsByHotelIdResponseModel> = MutableLiveData()
    val addRatingsResponse: LiveData<RatingsByHotelIdResponseModel> = _addRatingsResponse


    //booking history flow data using pagination from PastBookingPagingDataSource
    val bookingHistory: Flow<PagingData<BookingByUserIdResponseItems>> = Pager(PagingConfig(pageSize = 5)) {
        PastBookingPagingDataSource(api)
    }.flow
        .cachedIn(viewModelScope)



    fun addReview(comment: String, hotelId: String, token:String){
        val addReviewModel = HotelIdModel(comment, hotelId)

        viewModelScope.launch {
            try {
                val response = customerRepository.addCustomerReviewByHotelId(addReviewModel, token)
                if (response.isSuccessful){
                    try {
                        _addReviewResponse.value = response.body()
                    }catch (e:Exception){
                        _addReviewResponse.postValue(ReviewByHotelIdResponseModel(null, true, "Server Error", 500))
                    }
                }else{
                    _addReviewResponse.postValue(ReviewByHotelIdResponseModel(null, false, "An error occurred, try again later", 400))
                }
            }catch (e:Exception){
                _addReviewResponse.postValue(ReviewByHotelIdResponseModel(null, false, "Please, check internet connection", 500))
            }
        }
    }

    fun addRating(ratings:Int, hotelId:String, token:String){
        val addRatingsModel = HotelIdRatingsModel(ratings)

        viewModelScope.launch {
            try{
                val response = customerRepository.addCustomerRatingsByHotelId(addRatingsModel, hotelId, token)
                if (response.isSuccessful){
                    try {
                        _addRatingsResponse.value = response.body()
                    }catch (e:Exception){
                        _addRatingsResponse.postValue(RatingsByHotelIdResponseModel(null, false, "Server error", 500))
                    }
                }else{
                    _addRatingsResponse.postValue(RatingsByHotelIdResponseModel(null, false, "Unauthorized", 400))
                }
            }catch (e:Exception){
                _addRatingsResponse.postValue(RatingsByHotelIdResponseModel(null, false, "Please, check internet connection", 500))
            }
        }
    }
}