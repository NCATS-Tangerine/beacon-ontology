/*-------------------------------------------------------------------------------
 * The MIT License (MIT)
 *
 * Copyright (c) 2015-18 STAR Informatics / Delphinai Corporation (Canada) - Dr. Richard Bruskiewich
 * Copyright (c) 2017    NIH National Center for Advancing Translational Sciences (NCATS)
 * Copyright (c) 2015-16 Scripps Institute (USA) - Dr. Benjamin Good
 *                       
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *-------------------------------------------------------------------------------
 */

package bio.knowledge.ontology;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * 
 * @author Richard
 * Newer BiolinkModel (used for Gradle tests and beacons)
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class BeaconBiolinkModel {
	
	//private static Logger _logger = LoggerFactory.getLogger(BeaconBiolinkModel.class);

	private static final String BIOLINK_MODEL_YAML_PATH = "https://raw.githubusercontent.com/biolink/biolink-model/master/biolink-model.yaml";
	public static final String BIOLINK_MODEL_NAMESPACE = "BLM";
	
	private static BeaconBiolinkModel model = null ;
	
	private String id;
	private String name;
	private String description;
	private Map<String, String> prefixes;
	
	private Object types;
	private HashMap<String, BiolinkSlot> slots;
	private HashMap<String, BiolinkClass> classes;
	private List<BiolinkSlot> actualSlots = new ArrayList<>();
	private List<BiolinkClass> actualClasses = new ArrayList<>();
	
	public static BeaconBiolinkModel get() {
		Optional<BeaconBiolinkModel> optional = BeaconBiolinkModel.load();
		
		if (!optional.isPresent()) {
			throw new IllegalStateException("Could not load BiolinkModel");
		} else {
			return optional.get();
		}
	}
	
	public static Optional<BeaconBiolinkModel> load() {
		
		if (model != null) {
			return Optional.of(model);
		}
		
		URL yamlSource;
		try {
			
			// Option 1: construct a standard ObjectMapper
			yamlSource = new URL(BIOLINK_MODEL_YAML_PATH);
			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			model = mapper.readValue(yamlSource, BeaconBiolinkModel.class);
			
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(model);
	}
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Map<String, String> getPrefixes() {
		return prefixes;
	}


	public void setPrefixes(Map<String, String> prefixes) {
		this.prefixes = prefixes;
	}


	public Object getTypes() {
		return types;
	}


	public void setTypes(Object types) {
		this.types = types;
	}


	public List<BiolinkSlot> getSlots() {
		return actualSlots;
	}


	public void setSlots(List<BiolinkSlot> slots) {
		this.actualSlots = slots;
	}

	public List<BiolinkClass> getClasses() {
		return actualClasses;
	}


	public void setClasses(List<BiolinkClass> classes) {
		this.actualClasses = classes;
	}

	@JsonProperty("slots")
	public HashMap<String, BiolinkSlot> getTempSlots() {
		return slots;
	}

	@JsonProperty("slots")
	public void setTempSlots(HashMap<String, BiolinkSlot> slots) {
		for (String name : slots.keySet()) {
			BiolinkSlot slot = slots.get(name);
			slot.setName(name);
			actualSlots.add(slot);
		}
		
		this.slots = slots;
	}

	@JsonProperty("classes")
	public HashMap<String, BiolinkClass> getTempClasses() {
		return classes;
	}

	@JsonProperty("classes")
	public void setTempClasses(HashMap<String, BiolinkClass> classes) {
		for (String name : classes.keySet()) {
			BiolinkClass biolinkClass = classes.get(name);
			biolinkClass.setName(name);
			actualClasses.add(biolinkClass);
		}
		
		this.classes = classes;
	}
	
}
