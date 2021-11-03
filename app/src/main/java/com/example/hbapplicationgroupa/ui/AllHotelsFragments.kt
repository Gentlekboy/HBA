package com.example.hbapplicationgroupa.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hbapplicationgroupa.R
import com.example.hbapplicationgroupa.adapter.allHotelsAdapter.AllHotelsAdapter
import com.example.hbapplicationgroupa.database.AuthPreference
import com.example.hbapplicationgroupa.databinding.FragmentAllHotelsFragmentBinding
import com.example.hbapplicationgroupa.model.hotelmodule.allhotels.PageItem
import com.example.hbapplicationgroupa.viewModel.HotelViewModel
import com.example.hbapplicationgroupa.viewmodel.CustomerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllHotelsFragments : Fragment(),
                        AllHotelsAdapter.AllHotelsItemClickListener,
                        AllHotelsAdapter.AllHotelsBookBtnClickListener,
                        AllHotelsAdapter.AllHotelSaveIconClickListener{

    lateinit var allHotelsAdapter: AllHotelsAdapter
    val viewModel: HotelViewModel by viewModels()
    val customerViewModel: CustomerViewModel by viewModels()
    val arrayList =  ArrayList<PageItem>()
  //  val customerViewModel: CustomerViewModel by viewModels()
    lateinit var arrayLists : List<PageItem>
    lateinit var selectedState: String
    lateinit var hotelId: String

    //setting up view binding
    private var _binding: FragmentAllHotelsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllHotelsFragmentBinding.inflate(
            inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayLists = listOf()

        //Handling on back icon to go back to explore page
        binding.allHotelsBackBtn.setOnClickListener{ findNavController().popBackStack()}

        //setting recyclerview
        // To filter all hotel location
        val  autoCompleteTextView = binding.allHotelsFilters
        val languages = resources.getStringArray(R.array.states)
        val filterByAdapter = ArrayAdapter(requireContext(), R.layout.allhotel_autocompletetv_xml, languages)
            autoCompleteTextView.setAdapter(filterByAdapter)

        // to set filter_By textView to Location textView on the screen
        binding.allHotelsFilters.onItemClickListener = object :AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
              selectedState = languages[p2].toString()
              search(selectedState)
                binding.allHotelsLocationTxt.text = selectedState
                binding.allHotelsLocationTxt.visibility = View.VISIBLE

            }
        }

        onBackPressed()
        setupRecyclerView()
        //showing progress bar while api data is loading or no internet
        showProgressBar("loading hotels, Please, make sure your internet is active")

        showProgressBar()
        filterAllHotelByLocationObserver()

        //Observing viewModel
        viewModel.fetchAllHotels()
        viewModel.allHotelsLiveData.observe(viewLifecycleOwner, { response ->
            response.pageItems.let {responseList ->
                //Log.d("ObservingVM", response.pageItems.toString())
                    if (responseList != null) {
                        arrayLists = responseList
                        allHotelsAdapter.listOfAllHotels.addAll(responseList)
                    hideProgressBar()
                    allHotelsAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun allHotelsItemClicked(position: Int) {
        val item = arrayLists[position]
        hotelId = item.id!!
        val action = AllHotelsFragmentsDirections.actionAllHotelsFragmentsToHotelDescription2Fragment(hotelId)
        findNavController().navigate(action)
    }

    override fun allHotelsPreviewBtnClicked(position: Int) {
        val item = arrayLists[position]
        hotelId = item.id!!
        val action = AllHotelsFragmentsDirections.actionAllHotelsFragmentsToHotelDescription2Fragment(hotelId)
        findNavController().navigate(action)
    }

    private fun hideProgressBar() {
        binding.fragmentAllHotelsProgressBarPb.visibility = View.INVISIBLE
    }

    private fun showProgressBar(message: String = " Please, make sure your Internet is active") {
        binding.fragmentAllHotelsProgressBarPb.visibility = View.VISIBLE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    //set up recycler view
    private fun setupRecyclerView() {
        allHotelsAdapter = AllHotelsAdapter(this,
                                          this,
                                          this)
        binding.allHotelsRecyclerview.apply {
            adapter = allHotelsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    //Method to handle back press
    private fun onBackPressed(){
        //Overriding onBack press to navigate to home Fragment onBack Pressed
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }


    fun makeApiCallFilterAllHotelByLocation(location: String, pageSize: Int, pageNumber: Int){
        viewModel.filterAllHotelWithLocation(location, pageSize, pageNumber)
    }

    fun filterAllHotelByLocationObserver(){
        viewModel._filterAllHotelByLocationLiveData.observe(requireActivity(),{
            if (it == null){
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            }else{
        allHotelsAdapter.listOfAllHotels = it.data.pageItems as MutableList<PageItem>
                allHotelsAdapter.notifyDataSetChanged()
                //Log.d("All hotels: ", "${it.data.pageItems}")
                Toast.makeText(requireContext(),"${it.data.pageItems}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun search(selectedStates: String) {
        val selectedState =  viewModel.search(selectedStates)
        if (selectedState.isNotEmpty()){
            allHotelsAdapter.setList(selectedState)
            binding.tvNotificationAllHotels.text = ""
        }else{
            allHotelsAdapter.setList(selectedState)
            binding.tvNotificationAllHotels.text = "No Hotel in this Location"
        }
    }
    //set save icon to save hotel to wishList
     override fun allHotelSaveIconClickListener(position: Int) {
        AuthPreference.initPreference(requireActivity())
        val authToken = "Bearer ${AuthPreference.getToken(AuthPreference.TOKEN_KEY)}"
        val hotelWish = arrayLists[position]
        hotelWish.id?.let {
           customerViewModel.addWishList(authToken, it)
            customerViewModel.getWishList(authToken, 50, 1)
//            Toast.makeText(requireContext(),
//                "${arrayLists[position].name} is successfully deleted from WishList",
//                Toast.LENGTH_SHORT).show()
        }
    }


}