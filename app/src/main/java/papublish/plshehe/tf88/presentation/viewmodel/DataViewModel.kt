package papublish.plshehe.tf88.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import papublish.plshehe.tf88.common.model.DataModel
import papublish.plshehe.tf88.utils.StaticData.Companion.historyDescriptions

class DataViewModel : ViewModel() {

    private var displayList = ArrayList<DataModel>()
    private var displayMutableLiveData = MutableLiveData<List<DataModel>>()
    val displayLiveData get() = displayMutableLiveData

    private var coroutineEx = CoroutineExceptionHandler { _, _ ->
        displayMutableLiveData.postValue(null)
    }

    fun displayFun() {
        viewModelScope.launch(coroutineEx + Dispatchers.IO){
            for (n in historyDescriptions.indices){
                displayList.add(DataModel(historyDescriptions[n]))
            }
            displayMutableLiveData.postValue(displayList)
        }
    }


}