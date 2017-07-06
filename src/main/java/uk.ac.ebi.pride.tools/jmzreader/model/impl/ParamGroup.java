package uk.ac.ebi.pride.tools.jmzreader.model.impl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.pride.tools.jmzreader.model.Param;

/**
 * ParamGroup is a container for
 * UserParams and CvParams.
 * @author jg
 *
 */
public class ParamGroup {
	private List<CvParam> cvParams;
	private List<UserParam> userParams;
	
	public ParamGroup() {
		cvParams 	= new ArrayList<>();
		userParams 	= new ArrayList<>();
	}
	
	public void addParam(Param param) {
		if (param instanceof UserParam)
			userParams.add((UserParam) param);
		
		if (param instanceof CvParam)
			cvParams.add((CvParam) param);
	}
	
	public void removeParam(Param param) {
		if (param instanceof UserParam)
			userParams.remove(param);
		
		if (param instanceof CvParam)
			cvParams.remove(param);
	}
	
	public List<CvParam> getCvParams() {
		return new ArrayList<>(cvParams);
	}
	
	public List<UserParam> getUserParams() {
		return new ArrayList<>(userParams);
	}
	
	public List<Param> getParams() {
		ArrayList<Param> params = new ArrayList<>(cvParams);
		params.addAll(userParams);
		
		return params;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParamGroup that = (ParamGroup) o;

        return (cvParams != null ? cvParams.equals(that.cvParams) : that.cvParams == null) && (userParams != null ? userParams.equals(that.userParams) : that.userParams == null);
    }

    @Override
    public int hashCode() {
        int result = cvParams != null ? cvParams.hashCode() : 0;
        result = 31 * result + (userParams != null ? userParams.hashCode() : 0);
        return result;
    }
}
