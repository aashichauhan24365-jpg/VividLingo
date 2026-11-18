package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

val NunitoSansFontFamily = FontFamily(
  Font(R.font.nunito_sans, FontWeight.Normal),
  Font(R.font.nunito_sans, FontWeight.Medium),
  Font(R.font.nunito_sans, FontWeight.SemiBold),
  Font(R.font.nunito_sans, FontWeight.Bold),
  Font(R.font.nunito_sans, FontWeight.ExtraBold)
)

val AppTypography = Typography(
  displayLarge = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 36.sp,
    lineHeight = 44.sp,
    letterSpacing = (-0.02).sp,
    color = TextSlateHeadline
  ),
  headlineLarge = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 28.sp,
    lineHeight = 36.sp,
    letterSpacing = (-0.01).sp,
    color = TextSlateHeadline
  ),
  headlineMedium = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    color = TextSlateHeadline
  ),
  titleLarge = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    lineHeight = 26.sp,
    color = TextSlateHeadline
  ),
  titleMedium = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp,
    lineHeight = 22.sp,
    color = TextSlateHeadline
  ),
  bodyLarge = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    color = TextSlateBody
  ),
  bodyMedium = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = TextSlateBody
  ),
  bodySmall = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    color = TextSlateSubdued
  ),
  labelLarge = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.02.sp,
    color = TextSlateHeadline
  ),
  labelMedium = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 13.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.04.sp,
    color = TextSlateSubdued
  ),
  labelSmall = TextStyle(
    fontFamily = NunitoSansFontFamily,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 11.sp,
    lineHeight = 14.sp,
    letterSpacing = 0.06.sp,
    color = TextSlateMuted
  )
)
