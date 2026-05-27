package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composejoyride.data.utils.VengButtonType
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.liquid.LiquidButton
import com.example.composejoyride.ui.theme.liquid.LiquidGlassSupport
import com.example.composejoyride.ui.theme.liquid.rememberIsolatedLiquidBackdrop

@Composable
fun VengButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "",
    image: ImageVector? = null,
    icon: Int? = null,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
    textColor: Color = MaterialTheme.colorScheme.tertiary,
    isInteractive: Boolean = true,
    buttonType: VengButtonType
) {

    val colorScheme = MaterialTheme.colorScheme
    val liquidBackdrop = rememberIsolatedLiquidBackdrop(
        tintColor = colorScheme.secondary,
    )

    when (buttonType) {
        VengButtonType.Default -> {
            Button(
                modifier = modifier,
                onClick = onClick,
                colors = buttonColor,
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 5.dp
                )
            ) {
                Row(horizontalArrangement = Arrangement.Start) {
                    if (image != null) {
                        Icon(
                            imageVector = image,
                            contentDescription = "",
                            tint = textColor
                        )
                    } else if (icon != null) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "",
                            tint = textColor
                        )
                    }
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = Dimens.paddingLarge),
                        fontFamily = TheFont,
                        color = textColor,
                        fontSize = 22.sp
                    )
                }
            }
        }

        VengButtonType.Outlined -> {
            OutlinedButton(
                modifier = modifier,
                onClick = onClick,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, buttonColor.containerColor),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                    containerColor = Color.Transparent,
                    disabledContainerColor = colorScheme.surfaceVariant,
                    disabledContentColor = colorScheme.onSurface.copy(alpha = 0.38f)
                ),
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    if (image != null) {
                        Icon(
                            imageVector = image,
                            contentDescription = "",
                            tint = textColor
                        )
                    } else if (icon != null) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "",
                            tint = textColor
                        )
                    }
                    Text(
                        text = text,
                        modifier = Modifier
                            .padding(start = Dimens.paddingLarge),
                        fontFamily = TheFont,
                        color = textColor,
                        fontSize = 22.sp
                    )
                }
            }
        }

        VengButtonType.Icon -> {
            IconButton(
                modifier = modifier.border(
                    2.dp,
                    buttonColor.containerColor,
                    shape = RoundedCornerShape(12.dp)
                ),
                onClick = onClick,
                colors = IconButtonColors(
                    Color.Transparent,
                    Color.Transparent,
                    buttonColor.disabledContainerColor,
                    buttonColor.disabledContentColor
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    if (image != null) {
                        Icon(
                            imageVector = image ?: Icons.Default.BrokenImage,
                            contentDescription = "",
                            tint = textColor
                        )
                    } else if (icon != null)
                    {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "",
                            tint = textColor
                        )
                    } else
                    {
                        Icon(
                            imageVector = Icons.Default.BrokenImage,
                            contentDescription = "",
                            tint = textColor
                        )
                    }
                }
            }
        }

        VengButtonType.Liquid -> {
            val liquidContent: @Composable () -> Unit = {
                Row(horizontalArrangement = Arrangement.Start) {
                    if (image != null) {
                        Icon(
                            imageVector = image,
                            contentDescription = "",
                            tint = textColor
                        )
                    } else if (icon != null) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "",
                            tint = textColor
                        )
                    }
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = Dimens.paddingLarge),
                        fontFamily = TheFont,
                        color = textColor,
                        fontSize = 22.sp
                    )
                }
            }
            if (!LiquidGlassSupport.enabled) {
                Button(
                    modifier = modifier,
                    onClick = onClick,
                    colors = buttonColor,
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 5.dp
                    ),
                ) {
                    liquidContent()
                }
            } else {
                LiquidButton(
                    onClick = onClick,
                    backdrop = liquidBackdrop,
                    modifier = modifier,
                    isInteractive = isInteractive,
                    tint = buttonColor.contentColor,
                    surfaceColor = buttonColor.containerColor,
                    content = { liquidContent() },
                )
            }
        }
    }
}

@Preview(name = "Default button")
@Composable
fun VengButtonPreview() {
    Row(
        Modifier
            .size(200.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        VengButton(
            onClick = { },
            text = "Тест",
            image = Icons.Default.TipsAndUpdates,
            textColor = MaterialTheme.colorScheme.tertiary,
            buttonType = VengButtonType.Icon
        )
    }
}