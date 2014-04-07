package de.rob1n.prospam.filter.specific;

import de.rob1n.prospam.ProSpam;
import de.rob1n.prospam.chatter.Chatter;
import de.rob1n.prospam.filter.Filter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.UrlValidator;

public class FilterUrls extends Filter
{
	private final InetAddressValidator inetValidator = InetAddressValidator.getInstance();
	private final DomainValidator domainValidator = DomainValidator.getInstance();
	private final UrlValidator urlValidator = UrlValidator.getInstance();
	
	public FilterUrls(ProSpam plugin)
	{
		super(plugin);
	}

	@Override
	protected String executeFilter(final Chatter chatter, final String message)
	{
		String[] wordArr = message.split(" ");
		final int wordArrLength = wordArr.length;
		
		for(int i=0; i<wordArrLength; ++i)
		{			
			//wort nicht checken falls in whitelist
			if(isWhitelisted(wordArr[i]))
				continue;
			
			if(isURL(wordArr[i]))
				wordArr[i] = strings.filter_urls_blocked;
		}
		
		return StringUtils.join(wordArr, " ");
	}
	
	private boolean isURL(final String word)
	{
		return inetValidator.isValid(word) || domainValidator.isValid(word)|| urlValidator.isValid(word);
	}
}
