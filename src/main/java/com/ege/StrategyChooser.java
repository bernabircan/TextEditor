/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

import java.util.List;

/**
 *
 * @author EmreŞahin
 */
public class StrategyChooser {
    private Strategy strategy;

   public StrategyChooser(Strategy strategy){
      this.strategy = strategy;
   }
   
   //secili strategy i çalıştırır
   public List<String> executeStrategy(List<Yazi> metin, List<Kelime> sozluk) {
      return strategy.executeHataBulma(metin, sozluk);
   }

}
