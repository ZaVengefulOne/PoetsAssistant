package com.example.composejoyride.ui.widgets

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.composejoyride.PoemActivity
import com.example.composejoyride.ui.theme.DarkDeep
import com.example.composejoyride.ui.theme.DarkMid
import com.example.composejoyride.ui.theme.DarkSoft
import com.example.composejoyride.ui.theme.White
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@SuppressLint("RestrictedApi")
@Composable
fun PoemWidgetContent(
    title: String,
    preview: String,
) {
    val bg = ColorProvider(DarkDeep)
    val labelColor = ColorProvider(DarkSoft)
    val titleColor = ColorProvider(White)
    val bodyColor = ColorProvider(White.copy(alpha = 0.85f))
    val hintColor = ColorProvider(DarkMid)

    val dateText = LocalDate.now()
        .format(DateTimeFormatter.ofPattern("d MMMM", Locale("ru")))

    val shapeModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        GlanceModifier.cornerRadius(android.R.dimen.system_app_widget_background_radius)
    } else {
        GlanceModifier.cornerRadius(16.dp)
    }

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .then(shapeModifier)
            .background(bg)
            .clickable(actionStartActivity<PoemActivity>())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Стих дня",
                style = TextStyle(
                    color = labelColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                ),
            )
            Spacer(GlanceModifier.defaultWeight())
            Text(
                text = dateText,
                style = TextStyle(
                    color = hintColor,
                    fontSize = 11.sp,
                ),
            )
        }

        Spacer(GlanceModifier.height(8.dp))

        Text(
            text = title,
            style = TextStyle(
                color = titleColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            ),
            maxLines = 2,
        )

        Spacer(GlanceModifier.height(6.dp))

        Text(
            text = preview,
            style = TextStyle(
                color = bodyColor,
                fontSize = 13.sp,
            ),
            maxLines = 4,
        )

        Spacer(GlanceModifier.defaultWeight())

        Text(
            text = "Нажмите, чтобы открыть →",
            style = TextStyle(
                color = labelColor,
                fontSize = 11.sp,
            ),
        )
    }
}