/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ibe.hex.view.update;

/**
 *
 * @author MisterCavespider
 */
public @interface TickerListener {
    String ticker() default "main30fps";
}
