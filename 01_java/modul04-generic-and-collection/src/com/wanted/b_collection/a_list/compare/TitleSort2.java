package com.wanted.b_collection.a_list.compare;

import com.wanted.b_collection.a_list.dto.BookDTO;

import java.util.Comparator;

public class TitleSort2 implements Comparator<BookDTO> {


    @Override
    public int compare(BookDTO o1, BookDTO o2) {

        int result = 0;

        if (o1.getTitle().compareTo(o2.getTitle()) > o2.getTitle().compareTo(o1.getTitle())){
            result = -1;
        } else if (o2.getTitle().compareTo(o1.getTitle()) > o1.getTitle().compareTo(o2.getTitle())){
            result = 1;
        } else {
            result = 0;
        }

        return result;
    }
}
