package com.thirdparty.voucher;

import com.thirdparty.voucher.generator.CodeConfig;
import com.thirdparty.voucher.enums.Charset;
import com.thirdparty.voucher.generator.VoucherCodes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherGeneratorApplicationTests {

	@Test
	public void shouldGenerateCodeOfGivenLength() {
		// given
		CodeConfig config = CodeConfig.length(10);

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code.length()).isEqualTo(10);
	}

	@Test
	public void shouldGenerateAlphabeticCode() {
		// given
		CodeConfig config = CodeConfig.length(8).withCharset(Charset.ALPHABETIC);

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code).matches("^[a-zA-Z]*$");
	}

	@Test
	public void shouldGenerateAlphaNumericCode() {
		// given
		CodeConfig config = CodeConfig.length(8).withCharset(Charset.ALPHANUMERIC);

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code).matches("^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$");
	}

	@Test
	public void shouldGenerateNumericCode() {
		// given
		CodeConfig config = CodeConfig.length(8).withCharset(Charset.NUMBERS);

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code).matches("^([0-9]){8}$");
	}

	@Test
	public void shouldGenerateCodeWithPrefix() {
		// given
		CodeConfig config = CodeConfig.length(8).withPrefix("TEST-");

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code).startsWith("TEST-");
		assertThat(code.length()).isEqualTo(5 /*TEST-*/ + 8 /*random*/);
	}

	@Test
	public void shouldGenerateCodeWithPostfix() {
		// given
		CodeConfig config = CodeConfig.length(8).withPostfix("-TEST");

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code).endsWith("-TEST");
		assertThat(code.length()).isEqualTo(8 /*random*/ + 5 /*-TEST*/);
	}

	@Test
	public void shouldGenerateCodeWithPrefixAndPostfix() {
		// given
		CodeConfig config = CodeConfig.length(8).withPrefix("TE-").withPostfix("-ST");

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code).startsWith("TE-");
		assertThat(code).endsWith("-ST");
		assertThat(code.length()).isEqualTo(3 /*TE-*/ + 8 /*random*/ + 3 /*-ST*/);
	}

	@Test
	public void shouldGenerateCodeFromGivenPattern() {
		// given
		CodeConfig config = CodeConfig.pattern("##-###-##");

		// when
		String code = VoucherCodes.generate(config);

		// then
		assertThat(code).matches("^([0-9a-zA-Z]){2}-([0-9a-zA-Z]){3}-([0-9a-zA-Z]){2}$");
	}

}
