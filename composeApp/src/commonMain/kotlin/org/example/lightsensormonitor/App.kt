package org.example.lightsensormonitor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lightsensormonitor.composeapp.generated.resources.Res
import lightsensormonitor.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(
        shapes = MaterialTheme.shapes.copy(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp),
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            LightSensorCard(modifier = Modifier.width(240.dp))
        }
    }
}

@Composable
fun LightSensorCard(modifier: Modifier = Modifier) {
    val lightSensorMonitor = LocalLightSensorMonitor.current
    val lightSensorValue by lightSensorMonitor.value.collectAsStateWithLifecycle(initialValue = null)

    LightSensorCardContent(value = lightSensorValue, modifier = modifier)
}

@Composable
fun LightSensorCardContent(value: Float?, modifier: Modifier = Modifier) {
    BasicAppCard(
        title = "Light Sensor Monitor",
        valueDescription = "Value",
        value = value,
        modifier = modifier,
        headerPainter = painterResource(Res.drawable.compose_multiplatform),
        onCopyClick = {},
        onOpenDirectoryClick = {},
        onShareClick = {},
    )
}

@Composable
fun BasicAppCard(
    title: String,
    valueDescription: String,
    value: Float?,
    modifier: Modifier = Modifier,
    headerPainter: Painter? = null,
    onCopyClick: () -> Unit,
    onOpenDirectoryClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Card {
        Column(modifier = modifier) {
            CardHeader(painter = headerPainter, modifier = Modifier.fillMaxWidth().height(192.dp))

            CardContent(
                title = title,
                valueDescription = valueDescription,
                value = value,
                onCopyClick = onCopyClick,
                onOpenFolderClick = onOpenDirectoryClick,
                onShareClick = onShareClick,
            )
        }
    }
}

@Composable
private fun CardHeader(
    painter: Painter?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.1f))
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp,
        ) {
            if (painter != null) {
                Image(
                    painter = painter,
                    contentDescription = null, // Decorative
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
private fun CardContent(
    title: String,
    valueDescription: String,
    value: Float?,
    modifier: Modifier = Modifier,
    onCopyClick: () -> Unit = {},
    onOpenFolderClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = valueDescription,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(LocalContentColor.current.copy(alpha = 0.1F))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
            ) {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                )
            }
        }

        ActionsRow {
            IconButton(onClick = onCopyClick) {
                Icon(Icons.Outlined.FileCopy, contentDescription = "Copy")
            }

            VerticalDivider()

            IconButton(onClick = onOpenFolderClick) {
                Icon(Icons.Outlined.Folder, contentDescription = "Open Folder")
            }

            VerticalDivider()

            IconButton(onClick = onShareClick) {
                Icon(Icons.Outlined.Share, contentDescription = "Share")
            }

            VerticalDivider()

            IconButton(onClick = {}) {
                Icon(Icons.Outlined.MoreVert, contentDescription = "More")
            }
        }
    }
}

@Composable
private fun ActionsRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.secondary) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceEvenly,
            content = content,
        )
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .width(1.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.12f)),
    )
}
