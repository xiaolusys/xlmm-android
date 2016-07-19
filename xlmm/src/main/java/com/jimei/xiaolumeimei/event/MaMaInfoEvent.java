package com.jimei.xiaolumeimei.event;

import com.jimei.xiaolumeimei.entities.MamaFortune;

/**
 * Created by itxuye on 2016/7/19.
 */
public class MaMaInfoEvent {
  public MamaFortune mamaFortune;

  public MaMaInfoEvent(MamaFortune mamaFortune) {
    this.mamaFortune = mamaFortune;
  }

  public MamaFortune getMamaFortune() {
    return mamaFortune;
  }

  public void setMamaFortune(MamaFortune mamaFortune) {
    this.mamaFortune = mamaFortune;
  }
}
