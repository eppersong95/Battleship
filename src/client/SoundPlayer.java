package client;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;


public class SoundPlayer
{

  public static void playSound(String filePath) 
  {
    try
    {
      AudioInputStream sound = AudioSystem.getAudioInputStream(new File(filePath));
      Clip clip = AudioSystem.getClip();
      clip.open(sound);
      clip.start();
    }
    catch (UnsupportedAudioFileException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (LineUnavailableException e)
    {
      e.printStackTrace();
    }
  }
}
