create table fin_invoices (
	invoice_id SERIAL PRIMARY KEY,
	invoice_amount INT,
	invoice_currency VARCHAR(3),
	invoice_number VARCHAR(50),
	invoice_date DATE
);
