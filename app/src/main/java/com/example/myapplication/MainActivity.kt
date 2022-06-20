package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R.drawable
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val studentName = rememberSaveable { mutableStateOf("") }
                    val studentEmail = rememberSaveable { mutableStateOf("") }
                    val studentLocation = rememberSaveable { mutableStateOf("") }
                    val studentGrade = rememberSaveable { mutableStateOf("") }
                    val schoolName = rememberSaveable { mutableStateOf("") }
                    val dummy = rememberSaveable { mutableStateOf("") }
                    Column {
                        Text(
                            text = "Help us personalise\nyour learning experience",
                            style = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Center),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                        ) {


                            var interactionSource = remember { MutableInteractionSource() }
                            CustomTextField(modifier = Modifier
                                .wrapContentHeight(),
                                value = studentName.value,
                                onValueChange = { studentName.value = it },
                                interactionSource = interactionSource,
                                label = {
                                    Text(
                                        text = "Student’s Name*",
                                        style = if (interactionSource.collectIsFocusedAsState().value) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyLarge
                                    )
                                },
                                startDrawable = drawable.ic_onboard_student,
                                isError = true,
                                errorMessage = { Text(text = "error message") },
                                helperMessage = { Text(text = "helper message") })

                            CustomTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                value = studentEmail.value,
                                onValueChange = { studentEmail.value = it },
                                label = { Text(text = "Enter your Email ID *") },
                                startDrawable = drawable.ic_onboard_email
                            )
                            CustomTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                value = studentLocation.value,
                                onValueChange = { studentLocation.value = it },
                                label = { Text(text = "Select your location *") },
                                startDrawable = drawable.ic_onboard_location
                            )
                            CustomTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                value = studentGrade.value,
                                onValueChange = { studentGrade.value = it },
                                label = { Text(text = "Select Grade/Course *") },
                                startDrawable = drawable.ic_onboard_grade
                            )
                            CustomTextField(modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                                value = schoolName.value,
                                onValueChange = { schoolName.value = it },
                                label = { Text(text = "Enter School Name *") },
                                startDrawable = drawable.ic_onboard_school,
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = drawable.ic_arrow_down),
                                        contentDescription = null
                                    )
                                })
                        }

                        Button(
                            onClick = {
                                Toast.makeText(
                                    this@MainActivity, "button pressed", Toast.LENGTH_LONG
                                ).show()
                            },
                            modifier = Modifier
                                .padding(top = 52.dp)
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.gray))
                        ) {
                            Text(
                                text = "Start Learning",
                                color = Color.White,
                                modifier = Modifier.padding(
                                    top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        val studentName = rememberSaveable { mutableStateOf("") }
        var interactionSource = remember { MutableInteractionSource() }
        CustomTextField(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            value = studentName.value,
            onValueChange = { studentName.value = it },
            interactionSource = interactionSource,
            label = {
                Text(
                    text = "Student’s Name*",
                    style = if (interactionSource.collectIsFocusedAsState().value) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyLarge
                )
            },
            startDrawable = drawable.ic_onboard_student,
            isError = true,
            errorMessage = { Text(text = "error message", textAlign = TextAlign.Start) },
            helperMessage = { Text(text = "helper message") })
    }
}


