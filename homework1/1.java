class VideoLengthHomeWork {
    public static int minutesToSeconds(String videoLenght) {
      int colonIndex = videoLenght.indexOf(":");
      String minutes = videoLenght.substring(0, colonIndex);
      String seconds = videoLenght.substring(colonIndex + 1);
      if (seconds.compareTo("60") < 0) {
        return (Integer.parseInt(minutes) * 60 + Integer.parseInt(seconds));
      } else {
        return -1;
      }
    }
  }