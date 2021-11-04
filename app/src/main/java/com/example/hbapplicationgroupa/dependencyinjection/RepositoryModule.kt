package com.example.hbapplicationgroupa.dependencyinjection

import com.example.hbapplicationgroupa.database.dao.HotelByIdDao
import com.example.hbapplicationgroupa.database.dao.WishlistByPageNumberDao
import com.example.hbapplicationgroupa.network.*
import com.example.hbapplicationgroupa.repository.authmodulerepository.AuthRepository
import com.example.hbapplicationgroupa.repository.authmodulerepository.AuthRepositoryInterface
import com.example.hbapplicationgroupa.repository.customermodulerepository.CustomerRepository
import com.example.hbapplicationgroupa.repository.hotelmodulerepository.HotelRepository
import com.example.hbapplicationgroupa.repository.hotelmodulerepository.HotelRepositoryInterface
import com.example.hbapplicationgroupa.repository.paystackrepository.PaystackRepository
import com.example.hbapplicationgroupa.repository.usermodulerepository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(authModuleApiInterface: AuthModuleApiInterface): AuthRepository {
        return AuthRepository(authModuleApiInterface)
    }

    @Singleton
    @Provides
    fun provideAuthRepoInterface(authModuleApiInterface: AuthModuleApiInterface): AuthRepositoryInterface =
        AuthRepository(authModuleApiInterface)

    @Singleton
    @Provides
    fun provideCustomerRepository(
        customerModuleApiInterface: CustomerModuleApiInterface
    ): CustomerRepository {
        return CustomerRepository(customerModuleApiInterface)
    }

//    @Singleton
//    @Provides
//    fun provideHotelRepository(hotelModuleApiInterface: HotelModuleApiInterface): HotelRepository{
//        return HotelRepository(hotelModuleApiInterface)
//    }

//    @Singleton
//    @Provides
//    fun provideHotelRepository(hotelModuleApiInterface: HotelModuleApiInterface, hotelByIdDao: HotelByIdDao): HotelRepositoryInterface {
//        return HotelRepository(hotelModuleApiInterface, hotelByIdDao)
//    }

    @Singleton
    @Provides
    fun provideHotelRepositoryInterface(
        hotelModuleApiInterface: HotelModuleApiInterface,
        hotelByIdDao: HotelByIdDao,

    ): HotelRepositoryInterface{
        return HotelRepository(hotelModuleApiInterface, hotelByIdDao)
    }
//        fun provideHotelRepositoryInterface(
//            hotelModuleApiInterface: HotelModuleApiInterface,
//            hotelByIdDao: HotelByIdDao
//
//        ): HotelRepositoryInterface {
//            return HotelRepository(hotelModuleApiInterface, hotelByIdDao,)
//        }

        @Singleton
        @Provides
        fun provideUserRepository(userModuleApiInterface: UserModuleApiInterface): UserRepository {
            return UserRepository(userModuleApiInterface)
        }


    }