package duksung.android.hororok.ugeubi.retrofit.Search;

import java.io.Serializable;

public class MixtureItemDTO implements Serializable {
    String MIXTURE_ITEM_NAME;
    String PROHBT_CONTENT;

    public MixtureItemDTO(String MIXTURE_ITEM_NAME, String PROHBT_CONTENT) {
        this.MIXTURE_ITEM_NAME = MIXTURE_ITEM_NAME;
        this.PROHBT_CONTENT = PROHBT_CONTENT;
    }
}
