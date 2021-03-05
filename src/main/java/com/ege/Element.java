/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ege;

/**
 *
 * @author EmreŞahin
 */
public interface Element {
    void accept(Visitor v);
}
