package uk.ac.tees.mad.w9617422.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    var profileData = mutableStateOf(ProfileData(name = ""))
}