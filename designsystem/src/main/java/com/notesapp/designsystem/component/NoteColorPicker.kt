package com.notesapp.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class NoteColorOption(
    val color: Color,
    val label: String = ""
)

val DefaultNoteColors = listOf(
    NoteColorOption(Color(0xFFFFFFFF), "Default"),
    NoteColorOption(Color(0xFFF28B82), "Red"),
    NoteColorOption(Color(0xFFFBBC04), "Orange"),
    NoteColorOption(Color(0xFFFFF475), "Yellow"),
    NoteColorOption(Color(0xFFA8DAB5), "Green"),
    NoteColorOption(Color(0xFFA7FFEB), "Teal"),
    NoteColorOption(Color(0xFFCBF0F8), "Blue"),
    NoteColorOption(Color(0xFFD7AEFB), "Purple"),
    NoteColorOption(Color(0xFFFECDD3), "Pink")
)

@Composable
fun NoteColorPicker(
    selectedColor: Color,
    onColorSelected: (Color) -> Unit,
    colors: List<NoteColorOption> = DefaultNoteColors,
    itemSize: Dp = 32.dp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        colors.forEach { option ->
            val isSelected = option.color == selectedColor
            Box(
                modifier = Modifier
                    .size(itemSize)
                    .clip(CircleShape)
                    .background(option.color, CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier.border(2.dp, Color.Black.copy(alpha = 0.3f), CircleShape)
                        } else if (option.color == Color.White) {
                            Modifier.border(1.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
                        } else {
                            Modifier
                        }
                    )
                    .clickable { onColorSelected(option.color) }
            )
        }
    }
}
