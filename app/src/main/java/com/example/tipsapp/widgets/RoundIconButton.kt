package com.example.tipsapp.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
val IconbuttonSizeModifier=Modifier.size(40.dp)

@Composable
fun RoundIconButton(
    modifier: Modifier=Modifier,
    imageVector:ImageVector,
    onClick:()->Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor:Color=MaterialTheme.colorScheme.background,
    elevation: Dp =4.dp
) {

Card(modifier= Modifier
    .padding(all = 4.dp)
    .clickable { onClick.invoke() }
    .then(IconbuttonSizeModifier),
    shape = CircleShape,
    colors=CardDefaults.cardColors(backgroundColor),
    elevation=CardDefaults.cardElevation()) {
    Icon(modifier=Modifier.fillMaxSize(),imageVector = imageVector,
        contentDescription ="Plus or minus icon",
        tint=tint)

}

}