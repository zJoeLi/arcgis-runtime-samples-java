/*
 * Copyright 2019 Esri.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.esri.samples.create_and_save_kml_file;

import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import com.esri.arcgisruntime.layers.KmlLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchCreationMode;
import com.esri.arcgisruntime.ogc.kml.KmlDataset;
import com.esri.arcgisruntime.ogc.kml.KmlDocument;
import com.esri.arcgisruntime.ogc.kml.KmlPlacemark;

public class CreateAndSaveKMLFileController {

  @FXML private MapView mapView;
  @FXML private Label instructionsText;
  @FXML private GridPane geometrySelectionGridPane;
  @FXML private GridPane saveResetGridPane;
  @FXML private GridPane editingOptionsGridPane;
  @FXML private Button completeSketchBtn;

  private ArcGISMap map;
  private KmlDocument kmlDocument;
  private KmlDataset kmlDataset;
  private KmlLayer kmlLayer;
  private KmlPlacemark currentKmlPlacemark;

  @FXML
  public void initialize() {

    // create a map and add it to the map view
    map = new ArcGISMap(Basemap.createDarkGrayCanvasVector());
    mapView.setMap(map);

    ColorPicker colorPicker = new ColorPicker();

    // set the images for the point icon picker
    List<String> iconLinks = Arrays.asList(
            "http://static.arcgis.com/images/Symbols/Shapes/BlueCircleLargeB.png",
            "http://static.arcgis.com/images/Symbols/Shapes/BlueDiamondLargeB.png",
            "http://static.arcgis.com/images/Symbols/Shapes/BluePin1LargeB.png",
            "http://static.arcgis.com/images/Symbols/Shapes/BluePin2LargeB.png",
            "http://static.arcgis.com/images/Symbols/Shapes/BlueSquareLargeB.png",
            "http://static.arcgis.com/images/Symbols/Shapes/BlueStarLargeB.png");

    // set up a new KML document and layer
//    resetKmlDocument();
  }

  /**
   *
   */
  @FXML
  private void resetKmlDocument() {
    // clear any existing layers from the map
    map.getOperationalLayers().clear();

    // reset the most recently placed placemark
    currentKmlPlacemark = null;

    // create a new KmlDocument
    kmlDocument = new KmlDocument();
    kmlDocument.setName("KML Sample Document");

    // create a kml dataset uting the kml document
    kmlDataset = new KmlDataset(kmlDocument);

    // create the kml layer using the kml dataset.
    kmlLayer = new KmlLayer(kmlDataset);

    // add the kml layer to the map
    map.getOperationalLayers().add(kmlLayer);
  }

  @FXML
  private void resolveSelectGeometryClick(ActionEvent event){
    geometrySelectionGridPane.setVisible(false);
    completeSketchBtn.setVisible(true);
    saveResetGridPane.setVisible(false);

    // create variables for the sketch creation mode and color
    SketchCreationMode sketchCreationMode;

    // set the creation mode and UI based on which button called this method
    switch (((Button) event.getSource()).getText()){
      case "Point":
        sketchCreationMode = SketchCreationMode.POINT;
        instructionsText.setText("Click to add a point.");

        break;

      case "Polyline":
        sketchCreationMode = SketchCreationMode.POLYLINE;
        instructionsText.setText("Click to add a vertex.");
        break;

      case "Polygon":
        sketchCreationMode = SketchCreationMode.POLYGON;
        instructionsText.setText("Click to add a vertex.");
        break;
    }


  }

  @FXML
  private void resolveCompleteSketchClick(){
    geometrySelectionGridPane.setVisible(true);
    completeSketchBtn.setVisible(false);
    saveResetGridPane.setVisible(true);
  }

  @FXML
  private void handleSaveClick(){

  }

  /**
   * Stops and releases all resources used in application.
   */
  public void terminate() {

    if (mapView != null) {
      mapView.dispose();
    }
  }
}