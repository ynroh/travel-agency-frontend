package com.shadow_shift_studio.travelagency_frontend.view_model

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.travelagency_frontend.data.api_request.CountryRequest
import com.shadow_shift_studio.travelagency_frontend.data.api_request.ToursListRequest
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.Filter
import com.shadow_shift_studio.travelagency_frontend.domain.use_case.CountryUseCase
import com.shadow_shift_studio.travelagency_frontend.domain.use_case.ToursListUseCase
import com.shadow_shift_studio.travelagency_frontend.model.entity.Country
import com.shadow_shift_studio.travelagency_frontend.model.entity.TourPreview
import com.shadow_shift_studio.travelagency_frontend.model.enum.DurationCategory
import kotlinx.coroutines.launch

class CatalogViewModel(@SuppressLint("StaticFieldLeak") private val context: Context) : ViewModel() {

    val catalogTours: MutableLiveData<List<TourPreview>> = MutableLiveData()
    val countriesLiveData: MutableLiveData<List<Country>> = MutableLiveData()

    private val getCatalog: ToursListUseCase =
        ToursListUseCase(ToursListRequest())

    private val getCountry: CountryUseCase =
        CountryUseCase(CountryRequest())
    suspend fun getCountries() {
        viewModelScope.launch {
            val countries = getCountry.getCountries(context)
            countriesLiveData.value = countries
        }.join()
    }

    suspend fun getCatalog() {
        viewModelScope.launch {
            val allTours = getCatalog.getCatalog(context)

            val filteredTours = allTours.filter { tour ->
                Filter.selectedCountries.isEmpty() || tour.country.name in Filter.selectedCountries
                (Filter.selectedMinPrice.value <= tour.cost.toInt() && tour.cost.toInt() <= Filter.selectedMaxPrice.value)
                Filter.selectedDuration.isEmpty() || tour.stayDuration in Filter.selectedDurationToDouble
            }

            catalogTours.value = filteredTours
        }.join()
    }
}
