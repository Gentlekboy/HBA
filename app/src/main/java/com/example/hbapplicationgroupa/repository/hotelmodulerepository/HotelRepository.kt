package com.example.hbapplicationgroupa.repository.hotelmodulerepository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.hbapplicationgroupa.database.dao.HotelByIdDao
import com.example.hbapplicationgroupa.model.hotelmodule.getallhotels.GetAllHotelsResponseModel
import com.example.hbapplicationgroupa.model.hotelmodule.gethotelamenities.GetHotelAmenitiesResponseModel
import com.example.hbapplicationgroupa.model.hotelmodule.gethotelbyid.GetHotelByIdResponseItemData
import com.example.hbapplicationgroupa.model.hotelmodule.gethotelratings.GetHotelRatingsResponseModel
import com.example.hbapplicationgroupa.model.hotelmodule.gethotelroombyid.GetHotelRoomByIdResponseModel
import com.example.hbapplicationgroupa.model.hotelmodule.gethotelroomsbyprice.GetHotelRoomsByPriceResponseModel
import com.example.hbapplicationgroupa.model.hotelmodule.gethotelroomsbyvacancy.GetHotelRoomsByVacancyResponseModel
import com.example.hbapplicationgroupa.model.hotelmodule.gettopdeals.GetTopDealsResponseModel
import com.example.hbapplicationgroupa.model.hotelmodule.gettophotels.GetTopHotelsResponseModel
import com.example.hbapplicationgroupa.network.HotelModuleApiInterface
import retrofit2.Response
import javax.inject.Inject

class HotelRepository @Inject constructor(
    private val hotelModuleApiInterface: HotelModuleApiInterface,
    private val hotelByIdDao: HotelByIdDao
    ): HotelRepositoryInterface {

    override suspend fun getHotelByIdFromApi(hotelId: String) {
        val response = hotelModuleApiInterface.getHotelById(hotelId)
        val hotelDescription = response.body()?.data
        val statusCode = response.body()?.statusCode
        val message = response.body()?.message

        if (response.isSuccessful){
            if (hotelDescription != null) {
                saveHotelDescriptionToDb(hotelDescription)
            }else{
                Log.d("GKB", "getHotelByIdFromApi: Something went wrong --> hotelDescription is null. Status code = $statusCode. Message = $message")
            }
        }else{
            Log.d("GKB", "getHotelByIdFromApi: Something went wrong --> Response failed. Status code = $statusCode. Message = $message")
        }
    }

    override fun getHotelByIdFromDb(): LiveData<List<GetHotelByIdResponseItemData>> {
        return hotelByIdDao.getHotelById()
    }

    override suspend fun saveHotelDescriptionToDb(hotel: GetHotelByIdResponseItemData) {
        hotelByIdDao.insertHotel(hotel)
    }

    override suspend fun deleteHotelDescriptionFromDb(hotel: GetHotelByIdResponseItemData) {
        hotelByIdDao.removeHotel(hotel)
    }

    override suspend fun getTopHotels(): Response<GetTopHotelsResponseModel> {
        return hotelModuleApiInterface.getTopHotels()
    }

    override suspend fun getTopDeals(): Response<GetTopDealsResponseModel> {
        return hotelModuleApiInterface.getTopDeals()
    }
    override suspend fun getTopDealss(pageSize: Int, pageNumber: Int): Response<GetTopDealsResponseModel> {
        return hotelModuleApiInterface.getTopDealss(pageSize, pageNumber)
    }

    override suspend fun getAllHotels(
        Page: Int,
        pageSize: Int,
        IsBooked: Boolean,
        Price: Double,
        id: String
    ): Response<GetAllHotelsResponseModel> {
        return hotelModuleApiInterface.getAllHotels(Page, pageSize, IsBooked, Price, id)
    }

    override suspend fun getHotelRoomsByPrice(
        id: String,
        pageSize: Int,
        pageNumber: Int,
        isBooked: Boolean,
        price: Double
    ): Response<GetHotelRoomsByPriceResponseModel> {
        return hotelModuleApiInterface.getHotelRoomsByPrice(id, pageSize, pageNumber, isBooked, price)
    }

    override suspend fun getHotelRoomsByAvailability(
        pageSize: Int,
        pageNumber: Int,
        isBooked: Boolean
    ): Response<GetHotelRoomsByVacancyResponseModel> {
        return hotelModuleApiInterface.getHotelRoomsByAvailability(pageSize, pageNumber, isBooked)
    }

    override suspend fun getHotelRoomById(roomId: String): Response<GetHotelRoomByIdResponseModel> {
        return hotelModuleApiInterface.getHotelRoomById(roomId)
    }

    override suspend fun getHotelRatings(hotelId: String): Response<GetHotelRatingsResponseModel> {
        return hotelModuleApiInterface.getHotelRatings(hotelId)
    }

    override suspend fun getHotelAmenities(hotelId: String): Response<GetHotelAmenitiesResponseModel> {
        return hotelModuleApiInterface.getHotelAmenities(hotelId)
    }
}