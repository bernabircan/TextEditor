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
public interface Strategy {
    public List<String> executeHataBulma(List<Yazi> metin, List<Kelime> sozluk);
}
