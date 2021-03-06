package com.qiqi.jiaoyou_app.baiduface;

import java.net.*;
import java.util.Enumeration;
public class Test {

    public static void main(String[] args) {

	  System.out.println(getInet4Address());

    }


    public static String getInet4Address()
    {
	  Enumeration<NetworkInterface> nis;
	  String ip = "";
	  try
	  {
		nis = NetworkInterface.getNetworkInterfaces();
		for (; nis.hasMoreElements(); )
		{
		    NetworkInterface ni = nis.nextElement();
		    Enumeration<InetAddress> ias = ni.getInetAddresses();
		    for (; ias.hasMoreElements(); )
		    {
			  InetAddress ia = ias.nextElement();
			  //ia instanceof Inet6Address && !ia.equals("")
			  if (ia instanceof Inet4Address && !ia.getHostAddress().equals("127.0.0.1"))
			  {
				ip = ia.getHostAddress();
			  }
		    }
		}
	  }
	  catch (SocketException e)
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  return ip;
    }

}
