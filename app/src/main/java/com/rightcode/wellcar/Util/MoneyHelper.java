package com.rightcode.wellcar.Util;

import java.text.DecimalFormat;

public final class MoneyHelper

{
    public static DecimalFormat dfKor = new DecimalFormat("###,###");
    private static DecimalFormat dfUs = new DecimalFormat("###,##0.00");
    public static String getKor(int paramInt)
    {
        return getKor(String.valueOf(paramInt));
    }

    public static String getKor(String paramString)
    {
        String str = paramString;
        if ((str != null) && (!"".equals(str.trim())))
        {
            paramString = str;
            try
            {
                str = str.replaceAll(",", "");
                paramString = str;
                double d = Double.parseDouble(str);
                paramString = str;
                str = dfKor.format(Math.round(d));
                return str;
            } catch (Exception e)
            {
                e.printStackTrace();
                return paramString;
            }
        }
        return "0";
    }



    public static String getUsaUnit(int paramInt)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("₩");
        localStringBuilder.append(getKor(String.valueOf(paramInt)));
        return localStringBuilder.toString();
    }

    public static String getUsaUnit(String paramString)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("₩");
        localStringBuilder.append(getKor(paramString));

        return localStringBuilder.toString();
    }

    public static String getKorUnit(int paramInt)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(getKor(String.valueOf(paramInt)));
        localStringBuilder.append("원");
        return localStringBuilder.toString();
    }

    public static String getKorUnit(String paramString)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(getKor(paramString));
        localStringBuilder.append("원");
        return localStringBuilder.toString();
    }

    public static String getUs(int paramInt)
    {
        return getUs(String.valueOf(paramInt));
    }

    public static String getUs(String paramString)
    {
        if ((paramString != null) && (!"".equals(paramString.trim()))) {
            try
            {
                double d = Double.parseDouble(paramString);
                String str = dfUs.format(d);
                return str;
            } catch (Exception e)
            {
                e.printStackTrace();
                return paramString;
            }
        }
        return "0";
    }
}

