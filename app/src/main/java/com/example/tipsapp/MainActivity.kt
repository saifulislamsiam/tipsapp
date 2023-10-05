package com.example.tipsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MovableContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipsapp.ui.theme.TipsAppTheme
import com.example.tipsapp.util.calculateTotalPerPerson
import com.example.tipsapp.util.calculateTotalTip
import com.example.tipsapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAPP{
                Column {
                  // TopHeader()
                    MainContent()
                 //   Text("KI BOLOS RE BETTA")
                }


            }
        }
    }
}
@Composable
fun  MyAPP(content: @Composable () -> Unit) {

    
    TipsAppTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.padding(12.dp),
            color = MaterialTheme.colorScheme.background
            //color= Color.Magenta
        ) {
            content()

        }
    }
}
@Preview
@Composable
fun TopHeader(totalperPerson: Double=134.0)
{
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .height(150.dp)
        .clip(shape = CircleShape.copy(all = CornerSize(12.dp)))
      , color = Color(color = 0xFF36F4F4)
    ){
      Column(modifier = Modifier.padding(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center) {
           val total="%.2f".format(totalperPerson)
           Text(text = "Total per Person",
              style = MaterialTheme.typography.headlineMedium)
           Text(text = "$$total",
               style=MaterialTheme.typography.headlineMedium,
               fontWeight = FontWeight.ExtraBold
           )
      }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent(){
    BillForm(modifier=Modifier)
    {
      billAmt->
        Log.d("Tag","Maincontent:$billAmt")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier,
             onValChange:(String)->Unit) {
    var totalBillState = remember {
        mutableStateOf("")
    }
    var validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val KeyboardController = LocalSoftwareKeyboardController.current

    val sliferPositions = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliferPositions.value * 100).toInt()
    val splitByState = remember {
        mutableStateOf(1)
    }
    val range = IntRange(start = 1, endInclusive = 100)
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableStateOf(0.0)
    }
    TopHeader(totalperPerson = totalPerPersonState.value)
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),

        ) {
        Column(
            modifier = Modifier.padding((6.dp)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {


            InputField(valueState = totalBillState,
                labelId = "Enter Bill",

                enabled = true,

                isStringline = true,

                onAction = KeyboardActions
                {
                    if (!validState) return@KeyboardActions

                    onValChange(totalBillState.value.trim())

                    KeyboardController?.hide()
                })
            if (validState) {


                Row(
                    modifier = Modifier.padding(3.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(120.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(


                            imageVector = Icons.Default.Remove,
                            //logic for (-)
                            onClick = {
                                if (splitByState.value > 1) splitByState.value =
                                    splitByState.value - 1
                                else 1

                            })


                        Text(
                            text = "${splitByState.value}",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )

                        RoundIconButton(
                            imageVector = Icons.Default.Add,
                            //logic for(+)
                            onClick = {
                                if (splitByState.value < range.last) {
                                    splitByState.value = splitByState.value + 1


                                }
                            })

                    }

                }
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.Start
                )


                {
                    Text(
                        text = "Tip",

                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(200.dp))

                    Text(
                        text = "$ ${tipAmountState.value}",

                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,


                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = "${tipPercentage}%")

                    Spacer(modifier = Modifier.height(14.dp))

                    //Slider
                    Slider(
                        value = sliferPositions.value,

                        onValueChange = {

                                newVal ->
                            sliferPositions.value = newVal
                            tipAmountState.value = calculateTotalTip(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage
                            )
                            totalPerPersonState.value = calculateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                splitBy = splitByState.value,
                                tipPercentage = tipPercentage
                            )

                            Log.d("tag", "BillForm:$newVal")
                        },
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp
                        )
                    )


                }
            } else {
                Box() {}
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipsAppTheme {
        MyAPP{
            Text(text = "HEllo Again")
        }

    }
}