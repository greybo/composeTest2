package com.gurman.myapplication.feature.text

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.text.isDigitsOnly
import cafe.adriel.voyager.core.screen.Screen


class TextFieldPhoneTemplate : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val phoneNumberState = rememberTextFieldState()

        LaunchedEffect(phoneNumberState) {
            phoneNumberState.edit { // TextFieldBuffer scope
                append("123456789")
            }
        }

        Scaffold(topBar = {
            CenterAlignedTopAppBar(
                title = { Text("TextField phone template") },
//                modifier = Modifier
            )
        }) {
            BasicTextField(
                modifier = Modifier.padding(it),
                state = phoneNumberState,
                inputTransformation = { // TextFieldBuffer scope
                    if (asCharSequence().isDigitsOnly()) {
                        revertAllChanges()
                    }
                },
                outputTransformation = {
                    if (length > 0) insert(0, "(")
                    if (length > 4) insert(4, ")")
                    if (length > 8) insert(8, "-")
                }
            )
        }

    }
}