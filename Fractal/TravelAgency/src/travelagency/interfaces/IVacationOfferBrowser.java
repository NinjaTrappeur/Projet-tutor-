/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travelagency.interfaces;

import message.IOfferPack;
import message.IOfferRequest;

/**
 *
 * @author ninjatrappeur
 */
public interface IVacationOfferBrowser {
    public IOfferPack getProposals(IOfferRequest request);
}
