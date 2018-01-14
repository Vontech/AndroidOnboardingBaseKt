package org.vontech.rollout.views

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

/**
 * Created by vontell on 1/13/18.
 */
abstract class FilteredGroupSection<T>(sectionParameters: SectionParameters?) : StatelessSection(sectionParameters) {

    abstract fun add(model : T)
    abstract fun remove(model : T)
    abstract fun add(models : List<T>)
    abstract fun remove(models : List<T>)
    abstract fun replaceAll(models : List<T>)

}
