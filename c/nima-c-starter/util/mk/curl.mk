# Makefile for downloading and compiling libcURL
# If DL flag is set, download source from web, else check in stage/source dir
DLF := $(patsubst y,yes,$(DL))
curl:
	@echo "==> cURL"
	@echo "[!] Building libcURL"
	@echo ""
	@if [ ! -e $(PKG)/curl-$(CURL_VERSION).tar.gz ]; \
	then \
		if [ "$(DLF)" = "yes" ]; \
		then \
			echo "[o] DL enabled, downloading cURL source"; \
			wget -P $(PKG) http://curl.haxx.se/download/curl-$(CURL_VERSION).tar.gz; \
		else \
			echo "[x] Could not find libcurl source."; \
			echo "=> Please place source in stage/pkg or use DL=y command line option"; \
			exit 1; \
		fi \
	else \
		echo "[o] cURL source found, proceeding to build"; \
	fi

	@if [ ! -e $(PKG)/curl-$(CURL_VERSION)/Makefile ]; \
	then \
		echo "[!] No Makefile found. Configuring cURL"; \
		tar -C $(PKG) -xf $(PKG)/curl-$(CURL_VERSION).tar.gz; \
		cd $(PKG)/curl-$(CURL_VERSION); \
		./configure --build=$(HOST) \
					--host=$(HOST) \
					--prefix=$(EXT); \
	else \
		echo "[o] cURL already configured"; \
	fi

	@if [ ! -e $(EXT)/bin/curl ]; \
	then \
		echo "[!] Building cURL"; \
		$(MAKE) -C $(PKG)/curl-$(CURL_VERSION); \
		$(MAKE) -C $(PKG)/curl-$(CURL_VERSION) install; \
	else \
		echo "[o] cURL already built"; \
	fi

	@echo ""
	@echo ""

# [todo] - Make OpenSSL optional? If no SSL pass no SSL define to HttpClass?
ssl_check:
	@if [ -z "$(shell $(EXT)/bin/curl-config --feature | grep 'SSL')" ]; \
	then \
		echo "[x] libcURL built without SSL support"; \
		echo "=> Make sure OpenSSL is installed on the system, HTTPS requests require it"; \
		echo ""; \
		echo ""; \
		exit 1; \
	fi
