
package com.cnipr.pss.search;

/**
* java String 与各种进制字符之间的转换
* 
* @author 流水孟春^篱笆墙外 08.3.4
*/
public class Coding {

  /**
   * 字符串转换成十六进制值
   * @param bin String 我们看到的要转换成十六进制的字符串
   * @return 
   */
  public static String bin2hex(String bin) {
      char[] digital = "0123456789ABCDEF".toCharArray();
      StringBuffer sb = new StringBuffer("");
      byte[] bs = bin.getBytes();
      int bit;
      for (int i = 0; i < bs.length; i++) {
          bit = (bs[i] & 0x0f0) >> 4;
          sb.append(digital[bit]);
          bit = bs[i] & 0x0f;
          sb.append(digital[bit]);
      }
      return sb.toString();
  }

  /**
   * 十六进制转换字符串
   * @param hex String 十六进制
   * @return String 转换后的字符串
   */
  public static String hex2bin(String hex) {
      String digital = "0123456789ABCDEF";
      char[] hex2char = hex.toCharArray();
      byte[] bytes = new byte[hex.length() / 2];
      int temp;
      for (int i = 0; i < bytes.length; i++) {
          temp = digital.indexOf(hex2char[2 * i]) * 16;
          temp += digital.indexOf(hex2char[2 * i + 1]);
          bytes[i] = (byte) (temp & 0xff);
      }
      return new String(bytes);
  }

  /** 
   * java字节码转字符串 
   * @param b 
   * @return 
   */
  public static String byte2hex(byte[] b) { //一个字节的数，

      // 转成16进制字符串

      String hs = "";
      String tmp = "";
      for (int n = 0; n < b.length; n++) {
          //整数转成十六进制表示

          tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
          if (tmp.length() == 1) {
              hs = hs + "0" + tmp;
          } else {
              hs = hs + tmp;
          }
      }
      tmp = null;
      return hs.toUpperCase(); //转成大写

  }

  /**
   * 字符串转java字节码
   * @param b
   * @return
   */
  public static byte[] hex2byte(byte[] b) {
      if ((b.length % 2) != 0) {
          throw new IllegalArgumentException("长度不是偶数");
      }
      byte[] b2 = new byte[b.length / 2];
      for (int n = 0; n < b.length; n += 2) {
          String item = new String(b, n, 2);
          // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节

          b2[n / 2] = (byte) Integer.parseInt(item, 16);
      }
      b = null;
      return b2;
  }

  public static void main(String[] args) {
      String content = "技术性问题EDF%&^%#_|~";
      System.out.println(bin2hex(content));
      System.out.println(hex2bin("03D5;,&#x2211;,&#x007C;,&#x03BD;,&#x03A3;,&#x2212;,&#x504F;,&#x5DEE;,&#x03C6;,&#x00D7;,&#x91CD;,&#x7EC4;,&#x7387;,&#x4EA4;,&#x6362;,&#x578B;,&#x682A;,&#x6570;,&#x6297;,&#x6027;,&#x611F;,&#x0025;,&#x03B4;,&#x2207;,&#x2202;,&#x00AF;,&#x03B8;,&#x03C0;,&#x8840;,&#x6DB2;,&#x6D41;,&#x7ECF;,&#x6240;,&#x7528;,&#x7684;,&#x65F6;,&#x95F4;,&#x22C5;,&#x4E14;,&#x03BB;,&#x2229;,&#x2295;,&#x03B2;,&#x03C4;,&#x0027;,&#x03C9;,&#x03B5;,&#x2264;,&#x007B;,&#x007D;,&#x03B3;,&#x005E;,&#x03B1;,&#x0023;,&#x2227;,&#x3001;,&#x22EF;,&#x22EE;,&#x03BA;,&#x0398;,&#x02D9;,&#x2299;,&#x003E;,&#x003C;,&#x200B;,&#x03BC;,&#x03C1;,&#x221E;,&#x82E5;,&#x4E2D;,&#x91CF;,&#x2265;,&#x5176;,&#x5927;,&#x4E8E;,&#x2248;,&#x222B;,&#x0394;,&#x2192;,&#x03C3;,&#x2208;,&#x2261;,&#x221D;,&#x03C5;,&#x005F;,&#x6B8B;,&#x6E23;,&#x8D28;,&#x751F;,&#x7269;,&#x6709;,&#x673A;,&#x5316;,&#x6CB9;,&#x4EFD;,&#x6536;,&#x539F;,&#x00A8;,&#x03B7;,&#x03BE;,&#x03A6;,&#x76F8;,&#x5BF9;,&#x589E;,&#x503C;,&#x52A0;,&#x6837;,&#x7EC6;,&#x80DE;,&#x672C;,&#x5E95;,&#x7167;,&#x5982;,&#x679C;,&#x6EE1;,&#x8DB3;,&#x4E0E;,&#x5173;,&#x7CFB;,&#xFF0C;,&#x53D6;,&#x5426;,&#x5219;,&#x21D2;,&#x2194;,&#x2022;,&#x00B1;,&#x6C61;,&#x6CE5;,&#x75C5;,&#x60C5;,&#x6307;,&#x5404;,&#x7EA7;,&#x53F6;,&#x7247;,&#x53D1;,&#x8BE5;,&#x4EE3;,&#x8868;,&#x8C03;,&#x67E5;,&#x603B;,&#x6700;,&#x9AD8;,&#x9632;,&#x6CBB;,&#x6548;,&#x5904;,&#x7406;,&#x6591;,&#x76F4;,&#x5F84;,&#xFF08;,&#xFF09;,&#x2218;,&#x03A9;,&#x957F;,&#x6291;,&#x5236;,&#x836F;,&#x5E73;,&#x5747;,&#x9634;,&#x5B58;,&#x5728;,&#x2102;,&#x00B0;,&#x7EFC;,&#x5408;,&#x8BC4;,&#x5B9A;,&#x2286;,&#x2282;,&#x03C2;,&#x03C8;,&#x79EF;,&#x6781;,&#x5FC3;,&#x5883;,&#x6D88;,&#x2200;,&#x7EEA;,&#x4E8B;,&#x4EF6;,&#x2033;,&#x02DC;,&#x223C;,&#x0393;,&#x7535;,&#x6C60;,&#x53EF;,&#x5BB9;,&#x5F00;,&#x8DEF;,&#x538B;,&#x0026;,&#x4E3A;,&#x7A7A;,&#x767D;,&#x533A;,&#x9499;,&#x039B;,&#x22F1;,&#x2026;,&#x220F;,&#x567B;,&#x5429;,&#x8131;,&#x9664;,&#x53CD;,&#x6620;,&#x5165;,&#x53E3;,&#x542B;,&#x5668;,&#x51FA;,&#x8FDB;,&#x786B;,&#x00A0;,&#x5438;,&#x9644;,&#x5242;,&#x88C5;,&#x586B,Sigma;,&#x2211;,&#x003C;,&#x0394;,&#x007B,"));
  }
}