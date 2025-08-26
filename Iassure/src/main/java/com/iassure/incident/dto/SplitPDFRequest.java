package com.iassure.incident.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class SplitPDFRequest {

    private String originalFile;
    private String originalFilePath;
    private List<SplitIndex> splitIndexes;

}
