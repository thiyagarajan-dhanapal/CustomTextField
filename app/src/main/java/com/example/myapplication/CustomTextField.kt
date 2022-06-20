package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    startDrawable: Int = -1,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    helperMessage: @Composable (() -> Unit)? = null,
    errorMessage: @Composable (() -> Unit)? = null,
) {


        ConstraintLayout(Modifier.padding(start = 35.dp)) {

            var (icon, textField, errorBox) = createRefs()

            if (startDrawable > -1) {
                Icon(
                    modifier = Modifier
                        .constrainAs(icon) {
                            start.linkTo(parent.absoluteLeft)
                            bottom.linkTo(textField.bottom)
                            end.linkTo(textField.start)
                        }
                        ,
                    painter = painterResource(id = startDrawable),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
            NewTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
                    .clickable { MutableInteractionSource() }
                    .constrainAs(textField)
                    {
                        start.linkTo(icon.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                label = label,
                trailingIcon = trailingIcon,
                interactionSource = interactionSource,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 0.dp,
                    end = 0.dp,
                    top = 0.dp,
                    bottom = 3.dp
                ),
                isError = isError,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
                    .constrainAs(errorBox) {
                        start.linkTo(icon.end)
                        top.linkTo(textField.bottom)
                    }
                    .padding(top = 4.dp)
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides LocalTextStyle.current.copy(
                        fontSize = 12.sp,
                        color = if (isError) MaterialTheme.colors.error else LocalTextStyle.current.color
                    )
                ) {
                    if (isError) {
                        if (errorMessage != null) {
                            errorMessage()
                        }
                    } else {
                        if (helperMessage != null) {
                            CompositionLocalProvider(
                                LocalContentAlpha provides ContentAlpha.medium
                            ) {
                                helperMessage()
                            }
                        }
                    }
                }
            }
        }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = androidx.compose.material.MaterialTheme.shapes.small.copy(
        bottomEnd = ZeroCornerSize,
        bottomStart = ZeroCornerSize
    ),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
    contentPadding: PaddingValues =
        if (label == null) {
            TextFieldDefaults.textFieldWithoutLabelPadding()
        } else {
            TextFieldDefaults.textFieldWithLabelPadding()
        }
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterialApi::class)
    BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.backgroundColor(enabled).value, shape)
            .indicatorLine(enabled, isError, interactionSource, colors),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = contentPadding
            )
        }
    )
}