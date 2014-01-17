/*
 * Copyright 2012 Medical Research Council Harwell.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mousephenotype.dcc.entities.qc;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Gagarine Yaikhom <g.yaikhom@har.mrc.ac.uk>
 */
@Entity
public class CitedMeasurement implements Serializable {

    @Id
    private Integer a;
    private Long m;

    public CitedMeasurement() {
    }

    public CitedMeasurement(Integer animalId, Long measurementId) {
        this.a = animalId;
        this.m = measurementId;
    }

    public Long getM() {
        return m;
    }

    public void setM(Long measurementId) {
        this.m = measurementId;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer animalId) {
        this.a = animalId;
    }
};